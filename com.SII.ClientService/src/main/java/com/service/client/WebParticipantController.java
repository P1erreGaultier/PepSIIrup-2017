package com.service.client;


import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;

import serilogj.Log;
import serilogj.LoggerConfiguration;
import serilogj.core.LoggingLevelSwitch;
import serilogj.events.LogEventLevel;
import serilogj.sinks.seq.SeqSink;

/**
 * Rest Controller to use Partipant Service
 * @author Dorian Coqueron & Pierre Gaultier
 * @version 1.0
 */
@RestController
@Component
@CrossOrigin
public class WebParticipantController {

	private static final String ENCODE = "UTF-8";
	private static final String EXCHANGE = "exc.participant";
	@Value("${spring.application.name}")
	private String appName;


	public WebParticipantController(){
		LoggingLevelSwitch levelswitch = new LoggingLevelSwitch(LogEventLevel.Verbose);
		Log.setLogger(new LoggerConfiguration()		
			.writeTo(new SeqSink(Constants.getINSTANCE().getLogserverAddr(), Constants.getINSTANCE().getLogserverApikey(), null, Duration.ofSeconds(2), null, levelswitch))	
					.createLogger());
	}
	
	/**
	 * Method to get Participant by Event with RabbitMq
	 * @param id
	 * @return
	 */
    @RequestMapping("/getAllParticipantById")
    public String getAllParticipant(@RequestParam(value="id", defaultValue="1") String id){
    	String response = "";
    	try {
			response = new RabbitClient(EXCHANGE).rabbitRPCRoutingKeyExchange(id.getBytes(ENCODE),"getAllParticipantById");
		} catch (UnsupportedEncodingException e) {
			Log
			.forContext("MemberName", "getAllParticipantById")
			.forContext("Service", appName)
			.error(e,"UnsupportedEncodingException");
		}
		Log
		.forContext("MemberName", "getAllParticipantById")
		.forContext("Service", appName)
		.forContext("id", id)
		.information("Request : getAllParticipantById");
    	return response;
    }
    
	/**
	 * Method to add a participant to an event with RabbitMq
	 * @param id
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
    @RequestMapping(value = "/saveParticipant",method = RequestMethod.POST,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveParticipant(@RequestParam Map<String, String> body) throws UnsupportedEncodingException{
    	String person = body.get("person");
    	String event = body.get("event");
    	
    	String review ="{\"PersonId\":"+person+",\"EventId\":"+event+",\"Rate\": null,\"Text\": null}" ;
    	GoogleIdToken idToken = OauthTokenVerifier.checkGoogleToken(body.get("tokenid"));
		if (idToken != null) {
			Payload payload = idToken.getPayload();
			String userId = payload.getSubject();
			String email = payload.getEmail();
			String name = (String) payload.get("name");

			Log
			.forContext("id", body.get("tokenid"))
			.forContext("email", email)
			.forContext("userId", userId)
			.forContext("name", name)
			.forContext("Service", appName)
			.information("User Connection");		
			return new RabbitClient(EXCHANGE).rabbitRPCRoutingKeyExchange(review.getBytes(ENCODE),"addNewParticipant");
		} else {
			Log
			.forContext("Service", appName)
			.information("Invalid Token");
			return "{\"response\":\"error\"}";
		}		
    }
    
	/**
	 * Method to add a participant to an event with RabbitMq
	 * @param id
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
    @RequestMapping(value = "/cancelParticipation",method = RequestMethod.POST,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String cancelParticipation(@RequestParam Map<String, String> body){

    	String person = body.get("person");
    	String event = body.get("event");
    	String review ="{\"PersonId\":"+person+",\"EventId\":"+event+",\"Rate\": null,\"Text\": null}" ;
    	String res = null;

    	GoogleIdToken idToken = OauthTokenVerifier.checkGoogleToken(body.get("tokenid"));
		if (idToken != null) {
			Payload payload = idToken.getPayload();
			String userId = payload.getSubject();
			String email = payload.getEmail();
			String name = (String) payload.get("name");

			Log
			.forContext("id", body.get("tokenid"))
			.forContext("email", email)
			.forContext("userId", userId)
			.forContext("name", name)
			.forContext("Service", appName)
			.information("User Connection");		
			try {
				res = new RabbitClient(EXCHANGE).rabbitRPCRoutingKeyExchange(review.getBytes(ENCODE),"cancelParticipation");
			} catch (UnsupportedEncodingException e) {
				Log
				.forContext("MemberName", "registerPerson")
				.forContext("Service", appName)
				.error(e,"UnsupportedEncodingException");
				}
				return res;
			} 
			else {
			Log
			.forContext("Service", appName)
			.information("Invalid Token");
			return "{\"response\":\"error\"}";
		}		
    }
    
    
}
