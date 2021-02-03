package com.booth.service;

/**
 *
 * @date 2020-12-24 11:00
 */
public interface CommunicationService {
    /***
     * 发送http请求
     * @param msg
     * @return
     * @throws Exception
     */
    Object sendNReceive(Object msg) throws Exception;
}
