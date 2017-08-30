package com.moredo.common.utils.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class Session {

    @Autowired
    private HttpSession session;


    @SuppressWarnings("unused")
    private Session() {
    }

    public Session(HttpSession session) {
        super();

        this.session = session;
    }


    public Object get(String key) {

        if (session == null) {
            return null;
        }
        Object value = session.getAttribute(key);
        return value;
    }

    public void remove(String key) {
        if (session != null) {
            session.removeAttribute(key);
        }


    }

    public HttpSession getSession() {
        return session;
    }

    public void put(String key, Object value) {
        if (session == null) {
            return;
        }
        session.setAttribute(key, value);
    }


    public void clear() {
        if (session == null) {
            return;
        }
        session.invalidate();
    }

}
