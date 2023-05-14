package edu.agh.jabeda.server.adapters.in.web.exception;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class JabedaRestError {
    String message;
    String errorCode;
}
