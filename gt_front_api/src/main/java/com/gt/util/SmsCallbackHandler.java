package com.gt.util;


public abstract class SmsCallbackHandler {

	public SmsCallbackHandler(Object clientData) {
		this.clientData = clientData;
	}

	public SmsCallbackHandler() {
		clientData = null;
	}

	public Object getClientData() {
		return clientData;
	}

	public void receiveResultreplyConfirm(
			SmsStub.ReplyConfirmResponse replyconfirmresponse) {
	}

	public void receiveErrorreplyConfirm(Exception exception) {
	}

	public void receiveResultauditing(SmsStub.AuditingResponse auditingresponse) {
	}

	public void receiveErrorauditing(Exception exception) {
	}

	public void receiveResultreport(SmsStub.ReportResponse reportresponse) {
	}

	public void receiveErrorreport(Exception exception) {
	}

	public void receiveResultreply(SmsStub.ReplyResponse replyresponse) {
	}

	public void receiveErrorreply(Exception exception) {
	}

	public void receiveResultsms(SmsStub.SmsResponse smsresponse) {
	}

	public void receiveErrorsms(Exception exception) {
	}

	protected Object clientData;
}

