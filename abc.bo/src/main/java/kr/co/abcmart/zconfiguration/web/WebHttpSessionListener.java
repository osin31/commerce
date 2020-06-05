package kr.co.abcmart.zconfiguration.web;

import javax.servlet.annotation.WebListener;

import org.springframework.security.web.session.HttpSessionEventPublisher;

@WebListener
public class WebHttpSessionListener extends HttpSessionEventPublisher {

}