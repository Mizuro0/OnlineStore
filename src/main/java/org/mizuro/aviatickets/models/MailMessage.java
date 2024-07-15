package org.mizuro.aviatickets.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MailMessage {
    private String to;
    private String subject;
    private String text;
    private String token;
}
