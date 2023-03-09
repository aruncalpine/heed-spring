package com.zno.heed.user;

import java.io.IOException;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zno.heed.constants.CommonConstant.Lockout;
import com.zno.heed.constants.CommonConstant.ResponseCode;
import com.zno.heed.constants.CommonConstant.UserMessage;
import com.zno.heed.services.LoggerService;
import com.zno.heed.utils.DateCalculator;
import com.zno.heed.utils.ZnoQuirk;


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

		if(userInDB.getLastModified() != null)
			minu = DateCalculator.getHours(userInDB.getLastModified());

		if(userInDB.getAttempts() == 5 && minu < 360)
			throw new ZnoQuirk(ResponseCode.FAILED, Lockout.USER_LOCKED_ONE);
		else if((userInDB.getAttempts() == 10 && minu < 720))
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
	public User doLogin(User user, String ipAddress) throws ZnoQuirk {
		User userInDB = null;
		userInDB = userRepository.findByEmail(user.getEmail());

		// this is to be removed after the development is completed
		User userInDBwithPassword = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		boolean matches = encoder.matches(user.getPassword(), userRepository.findByEmail(user.getEmail()).getPassword());
		if(userInDB != null && matches || userInDBwithPassword != null ){
			user = userInDB;
			user.setUserToken(generateToken());
			user.setTokenTime(new Date());
			user.setAttempts(0);
			user.setLoginUcId(null);
			user.setLastModified(null);
			user.setLastLogin(new Date());
			userRepository.save(user);
		} else {	
			userInDB = userRepository.findByEmail(user.getEmail());
			if(userInDB != null) {
				if(userInDB.getAttempts() == 0)
					userInDB.setLastModified(new Date());

				LoginHistory loginHistory = new LoginHistory(userInDB, ipAddress, new Date(), new Date());
				loginHistoryRepository.save(loginHistory);				

				if(userInDB.getAttempts() < 5) {
					userInDB.setAttempts(userInDB.getAttempts()+1);	
					if(userInDB.getAttempts() == 5) {
						userInDB.setLastModified(new Date());					
						userRepository.save(userInDB);		
					} else {	
						userRepository.save(userInDB);
						throw new ZnoQuirk(ResponseCode.FAILED, UserMessage.ERROR_LOGIN_INVALID_USER);		
					}

				} else if(userInDB.getAttempts() < 10) {
					userInDB.setAttempts(userInDB.getAttempts()+1);
					if(userInDB.getAttempts() == 10) {
						userInDB.setLastModified(new Date());
						userRepository.save(userInDB);
					} else {
						userRepository.save(userInDB);
						throw new ZnoQuirk(ResponseCode.FAILED, UserMessage.ERROR_LOGIN_INVALID_USER);	
					}

				} else if(userInDB.getAttempts() < 15) {
					userInDB.setAttempts(userInDB.getAttempts()+1);	
					if(userInDB.getAttempts() == 15) {
						userInDB.setLastModified(new Date());
						userInDB.setAccountLocked(true);
						userRepository.save(userInDB);
					} else {
						userRepository.save(userInDB);
						throw new ZnoQuirk(ResponseCode.FAILED, UserMessage.ERROR_LOGIN_INVALID_USER);
					}
				} else if(userInDB.getAttempts() >= 15){
					userInDB.setAccountLocked(true);		
					userRepository.save(userInDB);
					throw new ZnoQuirk(ResponseCode.FAILED, Lockout.USER_LOCKED_THREE_OUT);

				}else {
					throw new ZnoQuirk(ResponseCode.FAILED, UserMessage.ERROR_LOGIN_INVALID_USER);					
				}
			}
		}
		
		return user;
	}
	
	private String generateToken() {
		return UUID.randomUUID().toString();
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
	public User saveUser(User user) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
		user.setFullName(user.getFirstName() + " "+ user.getLastName());
        UsersRole userRole = usersRoleRepository.findByRoleName("ADMIN");        
        user.setUsersRole(userRole);
		user.setEnabled(true);
		user.setPasswordUpdateDate(new Date());
		user.setDateCreated(new Date());
		user.setLastModified(new Date());
		userRepository.save(user);
		return user;        
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
}