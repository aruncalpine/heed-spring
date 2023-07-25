package com.zno.heed.user;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.zno.heed.MysqlEntites.LoginHistory;
import com.zno.heed.MysqlEntites.User;
import com.zno.heed.MysqlEntites.UsersRole;
import com.zno.heed.MysqlRepositories.LoginHistoryRepository;
import com.zno.heed.MysqlRepositories.UsersRepository;
import com.zno.heed.MysqlRepositories.UsersRoleRepository;
import com.zno.heed.constants.CommonConstant.Lockout;
import com.zno.heed.constants.CommonConstant.ResponseCode;
import com.zno.heed.constants.CommonConstant.UserMessage;
import com.zno.heed.services.LoggerService;
import com.zno.heed.utils.DateCalculator;
import com.zno.heed.utils.ZnoQuirk;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 
 * This Class may contain confidential, proprietary or legally privileged information. 
 * It is for the use of the ZNO only, and access by anyone else is unauthorized.
 * 
 * Module       : zno_online_class
 * Created Date : 28/07/2020
 * Created By   : TITTU VARGHESE
 */
@Service
public class UserService implements UserDetailsService {

	@Value("${expiry.time}")
	private String EXPIRY_TIME;

	@Value("${timeout}")
	private String TIMEOUT;

	@Value("${expiry.min}")
	private String EXPIRATION_MIN;

	@Value("${expiry.hours}")
	private String EXPIRATION_HOURS;

	@Value("${expiry.days}")
	private String EXPIRATION_DAYS;
	
	@Autowired
	private UsersRepository userRepository;

	@Autowired
	private UsersRoleRepository usersRoleRepository;
	
	@Autowired
	private LoginHistoryRepository loginHistoryRepository;
	
	@Autowired
	private LoggerService _logService;

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	public User findOne(Long id) {
		return userRepository.findById(id).orElse(null);
	}
	
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public User getUserFromToken(String token) {		
		User user = null;
		user = userRepository.findByUserToken(token);
		return user;
	}
	
	@Transactional
	public Boolean getlock(User user) throws ZnoQuirk {
		Boolean flag = false;
		long minu = 0;		
		User userInDB = userRepository.findByEmail(user.getEmail());

		if(!userInDB.getEnabled()) {
			logger.info(_logService.logMessage(userInDB.getEmail(), UserMessage.ACCOUNT_DISABLED, this.getClass().getName()));
			throw new ZnoQuirk(ResponseCode.FAILED, UserMessage.ACCOUNT_DISABLED);
		}

		if(userInDB.getAccountLocked() == true) {
			logger.info(_logService.logMessage(userInDB.getEmail(), Lockout.USER_LOCKED_THREE_OUT_MSG, this.getClass().getName()));
			throw new ZnoQuirk(ResponseCode.FAILED, Lockout.USER_LOCKED_THREE_OUT);	
		}

		if(userInDB.getPasswordExpired() == true) {
			//sendPasswordTokenForExpired(userInDB, uri);
			throw new ZnoQuirk(ResponseCode.FAILED, UserMessage.PASSWORD_EXPIRED);	
		}

		if(userInDB.getLastModified()!= null)
			minu = DateCalculator.getHours(userInDB.getLastModified());

		if(userInDB.getAttempts()==5 && minu < 360) {
			System.out.println("user locked one");
			throw new ZnoQuirk(ResponseCode.FAILED, Lockout.USER_LOCKED_ONE);	
		}
		else if((userInDB.getAttempts()==10 && minu < 720))
			throw new ZnoQuirk(ResponseCode.FAILED, Lockout.USER_LOCKED_TWO);
		else
			flag = true;		
		return flag;
	} 	

	/**
	 * 
	 * @param user
	 * @param uri
	 * @return
	 * @throws ZnoExceptionResponse
	 * @throws IOException 
	 */
	@Transactional  
	public UserResponse doLogin(LoginRequest loginUser, BindingResult bindingResult, HttpServletRequest request ) throws ZnoQuirk {
		String ipAddress = request.getRemoteAddr();                 
		if(bindingResult.hasErrors()) throw new ZnoQuirk(ResponseCode.FAILED, UserMessage.INVALID_INPUT_DATA);
		User userInDB = userRepository.findByEmail(loginUser.getEmail());
		
		if(userInDB == null) 
			throw new ZnoQuirk(ResponseCode.FAILED, UserMessage.ERROR_LOGIN_INVALID_USER);
		
		Boolean flag = getlock(userInDB);
		System.out.println(" Flag    "+flag   );
		if(flag == true) {
			
			// do later
			//calculateAndSetDaysLeftForPasswordExpiry(userInDB);
			
			PasswordEncoder encoder = new BCryptPasswordEncoder();
			boolean matches = encoder.matches(loginUser.getPassword(),userInDB.getPassword());
			System.out.println(loginUser.getPassword()+" "+userInDB.getPassword());
			System.out.println(matches);
			if(matches){
				System.out.println("is matches working");
				User user = userInDB;
				user.setUserToken(generateToken());
				user.setTokenTime(new Date());
				user.setAttempts(0);
				user.setLoginUcId(null);
				user.setLastModified(null);
	//			user.setLastlogin(new Date());
				userRepository.save(user);
	//			List<NXPMPReference> roles = new ArrayList<NXPMPReference>();
	//			roles = mapRepo.findRoleById(user);
	//			user.setRoles(roles);
				UserResponse userResponse = new UserResponse(userInDB, 1, null, false, false, null);
				logger.info(_logService.logMessage(userInDB.getEmail() +" : "+ UserMessage.USER_LOGIN, userInDB.getUserToken(), this.getClass().getName()));
				return userResponse;
			} 
			if(userInDB.getAttempts() == 0)
				userInDB.setLastModified(new Date());
			LoginHistory loginHistory = new LoginHistory(userInDB, ipAddress, new Date(), new Date());
			loginHistoryRepository.save(loginHistory);				
			if(userInDB.getAttempts() < 3) {
				userInDB.setAttempts(userInDB.getAttempts()+1);	
				if(userInDB.getAttempts() == 3) {
					userInDB.setLastModified(new Date());					
					userRepository.save(userInDB);		
				} else {	
					userRepository.save(userInDB);
					throw new ZnoQuirk(ResponseCode.FAILED, Lockout.USER_LOCKED_ONE);	
				}

			} else if(userInDB.getAttempts() < 6) {
				userInDB.setAttempts(userInDB.getAttempts()+1);
				if(userInDB.getAttempts() == 6) {
					userInDB.setLastModified(new Date());
					userRepository.save(userInDB);
				} else {
					userRepository.save(userInDB);
					throw new ZnoQuirk(ResponseCode.FAILED, Lockout.USER_LOCKED_TWO);
				}

			} else if(userInDB.getAttempts() < 9) {
				userInDB.setAttempts(userInDB.getAttempts()+1);	
				if(userInDB.getAttempts() == 9) {
					userInDB.setLastModified(new Date());
					userInDB.setAccountLocked(true);
					userRepository.save(userInDB);
				} else {
					userRepository.save(userInDB);
					throw new ZnoQuirk(ResponseCode.FAILED, Lockout.USER_LOCKED_THREE);
				}
			} else if(userInDB.getAttempts() >= 9){
				userInDB.setAccountLocked(true);		
				userRepository.save(userInDB);
						throw new ZnoQuirk(ResponseCode.FAILED, Lockout.USER_LOCKED_THREE_OUT);

			}else {
				throw new ZnoQuirk(ResponseCode.FAILED, UserMessage.ERROR_LOGIN_INVALID_USER);					
			}
		}		
		flag=getlock(userInDB);
		UserResponse userResponse = new UserResponse(userInDB, 1, null, false, false, null);
		logger.info(_logService.logMessage(userInDB.getEmail() +" : "+ UserMessage.USER_LOGIN, userInDB.getUserToken(), this.getClass().getName()));
		return userResponse;
	}
	
	private String generateToken() {
		return UUID.randomUUID().toString();
	}
	
	@Transactional  
	public UserResponse signUpLogin(User loginUser ) throws ZnoQuirk {
		System.out.println("getting into signUp");
		User userInDB = userRepository.findByEmail(loginUser.getEmail());
		if(userInDB == null) 
			throw new ZnoQuirk(ResponseCode.FAILED, UserMessage.ERROR_LOGIN_INVALID_USER);  
			// do later
	//		calculateAndSetDaysLeftForPasswordExpiry(userInDB);
			
			PasswordEncoder encoder = new BCryptPasswordEncoder();
			boolean matches = encoder.matches(loginUser.getPassword(),userInDB.getPassword());
			if(matches){
				User user = userInDB;
				user.setUserToken(UUID.randomUUID().toString());
				user.setTokenTime(new Date());
				user.setAttempts(0);
				user.setLoginUcId(null);
				user.setLastModified(null);
	//			user.setLastlogin(new Date());
				userRepository.save(user);
			   }
	//			List<NXPMPReference> roles = new ArrayList<NXPMPReference>();
	//			roles = mapRepo.findRoleById(user);
	//			user.setRoles(roles);
				UserResponse userResponse = new UserResponse(userInDB, 1, null, false, false, null);
				logger.info(_logService.logMessage(userInDB.getEmail() +" : "+ UserMessage.USER_LOGIN, userInDB.getUserToken(), this.getClass().getName()));
				return userResponse;
		}
	
	@Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);  
        if(user != null) {
            List<GrantedAuthority> authorities = getUserAuthority(user.getUsersRole().getRoleName());
            return buildUserForAuthentication(user, authorities);
        } else {
            throw new UsernameNotFoundException("username not found");
        }
    }

    private List<GrantedAuthority> getUserAuthority(String userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(userRoles));

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }

    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
	
	public Boolean logout(String userToken) {
		User user = userRepository.findByUserToken(userToken);
		if(user != null) {
			user.setLastModified(new Date());
			user.setAttempts(0);
			user.setUserToken(null);
			userRepository.save(user);
			return true;
		}else
			return false;
	}
 
	@Transactional
	public UserResponse saveUser(User user)throws ZnoQuirk {		
		User userExists = findByEmail(user.getEmail());		
		if (userExists != null) throw new ZnoQuirk(ResponseCode.FAILED, UserMessage.USER_EXISIT);
		PasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setConfirmPassword(encoder.encode(user.getPassword()));
		user.setFullName(user.getFirstName() + " "+ user.getLastName());
        UsersRole userRole = usersRoleRepository.findByRoleName("APPUSER");        
        user.setUsersRole(userRole);
		user.setEnabled(true);
		user.setPasswordUpdateDate(new Date());
		user.setDateCreated(new Date());
		user.setLastModified(new Date());
		
		System.out.println(user);
		
		userRepository.save(user);
		
//	C from D	UserResponse userResponse = signUpLogin(user);
		
		UserResponse userResponse = new UserResponse(user, 1, null, false, false, null);		
		return userResponse;        
	}
	
	public User checkAdminToken(String token) throws ZnoQuirk {
		User admin = userRepository.findByUserTokenAndAccountLockedAndEnabledAndPasswordExpired(token, false, true, false);
		if(admin == null)
			throw new ZnoQuirk(ResponseCode.FAILED, UserMessage.ERROR_LOGIN_INVALID_USER);
		else
			if(TimeUnit.MILLISECONDS.toMinutes(new Date().getTime() - admin.getTokenTime().getTime()) > Integer.valueOf(EXPIRY_TIME))
				throw new ZnoQuirk(ResponseCode.FAILED, UserMessage.ERROR_LOGIN_INVALID_USER);
			else {
				admin.setTokenTime(new Date());
				userRepository.save(admin);
			}
		return admin;
	}
	
	public void logOut(String barerToken){
		User user = userRepository.findByUserToken(barerToken);
		String email = user.getEmail();
		userRepository.setValueForUserToken(email);
	}	
	
	public void saveProfileImage(String barerToken, Path filePath) {
		String path = filePath.toString();
		userRepository.setProfileImagePath(path, barerToken);
	}
}