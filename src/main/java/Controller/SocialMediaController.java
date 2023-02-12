package Controller;

import Model.Account;
import Service.AccountService;
import Model.Message;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;
import java.util.ArrayList;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    AccountService accountService;
    MessageService messageService;

    
    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerAccountHandler); 
        app.post("/login", this::loginAccountHandler);
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIDHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIDHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages",this::getMessagesByAccountHandler);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void registerAccountHandler(Context context) throws JsonProcessingException{

        ObjectMapper mapper = new ObjectMapper();

        Account account = mapper.readValue(context.body(), Account.class);
        Account newAccount = accountService.addAccount(account);

        if(account.username != "" && account.password.length() > 4 && newAccount !=null){
            context.json(mapper.writeValueAsString(newAccount));
            context.status(200);
        }
        else{
            context.status(400);
        }
    }

    private void loginAccountHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account registeredUser = accountService.login(account);

        if(registeredUser != null){
            context.json(mapper.writeValueAsString(registeredUser));
            context.status(200);
        }
        else{
            context.status(401);
        }
    }

    private void createMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();

        Message message = mapper.readValue(context.body(), Message.class);
        Message newMessage = messageService.createMessage(message);

        if(message.message_text != "" && message.message_text.length() < 255 && newMessage != null){
            context.json(mapper.writeValueAsString(newMessage));
            context.status(200);
        }
        else{
            context.status(400);
        }
    }

    private void getAllMessagesHandler(Context context) throws JsonProcessingException{
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
        context.status(200);
    }

    public void getMessageByIDHandler(Context context) throws JsonProcessingException{
        int messageID = Integer.parseInt(context.pathParam("message_id"));
        Message getMessage = messageService.getMessageByID(messageID);

        if(getMessage != null){
            context.json(getMessage);
            context.status(200);
        }
    }

    public void deleteMessageByIDHandler(Context context) throws JsonProcessingException{
        int messageID = Integer.parseInt(context.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessageByID(messageID);

        System.out.println(deletedMessage);
        if(deletedMessage != null){
            context.json(deletedMessage);
            context.status(200);
        }
        else{
            context.status(200);
        }
    }

    public void updateMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();

        Message message = mapper.readValue(context.body(), Message.class);

        int messageId = Integer.parseInt(context.pathParam("message_id"));

        Message updatedMessage = messageService.updateMessage(messageId, message);

        if(updatedMessage == null || message.message_text.length() > 255 || message.message_text == ""){
            context.status(400);
        }
        else{
            context.json(mapper.writeValueAsString(updatedMessage));
            context.status(200);
        }
    }

    public void getMessagesByAccountHandler(Context context) throws JsonProcessingException{

        int accountId = Integer.parseInt(context.pathParam("account_id"));

        context.json(messageService.getMessagesByAccount(accountId));
        context.status(200);
    }
}