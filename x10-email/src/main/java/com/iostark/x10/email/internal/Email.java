/*
 * Copyright (C) 2015 IO Stark
 *
 * Licensed under licensing terms in LICENSES.md
 */
package com.iostark.x10.email.internal;

import com.iostark.x10.base.annotation.api.API_Internal;
import java.util.ArrayList;
import java.util.List;

/**
 * An email.
 *
 * @since V0_5_0
 */
@API_Internal
public class Email {
  private List<String> toRecipients;
  private String subject;
  private String body;

  /**
   * @since V0_5_0
   * @return
   */
  public List<String> getToRecipients() {
    return toRecipients;
  }

  /**
   * @since V0_5_0
   * @param toRecipients
   */
  public void setToRecipients(final List<String> toRecipients) {
    this.toRecipients = toRecipients;
  }

  /**
   * @since V0_5_0
   * @param recipient
   */
  @SuppressWarnings("PMD.LawOfDemeter")
  public void setToRecipient(final String recipient) {
    final List<String> recipients = new ArrayList<String>();
    recipients.add(recipient);
    this.toRecipients = recipients;
  }

  /**
   * @since V0_5_0
   * @return
   */
  public String getSubject() {
    return subject;
  }

  /**
   * @since V0_5_0
   * @param subject
   */
  public void setSubject(final String subject) {
    this.subject = subject;
  }

  /**
   * @since V0_5_0
   * @return
   */
  public String getBody() {
    return body;
  }

  /**
   * @since V0_5_0
   * @param body
   */
  public void setBody(final String body) {
    this.body = body;
  }
}
