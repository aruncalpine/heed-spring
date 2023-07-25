package com.zno.heed.nettySocket.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {
 UUID id;
 Long chatUserId;
}
