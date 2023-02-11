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

    public SocialMediaController(){
        this.accountService = new AccountService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerAccountHandler); 
        app.post("/login", this::loginAccountHandler);
        app.post("/messages", this::createMessageHandler);
        app.get("messages", this::getAllMessagesHandler);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void registerAccountHandler(Context context) throws JsonProcessingException{

        ObjectMapper mapper = new ObjectMapper();

        Account account = mapper.readValue(context.body(), Account.class);
        Account newAccount = AccountService.addAccount(account);

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
        Account registeredUser = AccountService.login(account);

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
        Message newMessage = MessageService.createMessage(message);

        System.out.println(newMessage);

        if(message.message_text != "" && message.message_text.length() < 255 && newMessage != null){
            context.json(mapper.writeValueAsString(newMessage));
            context.status(200);
        }
        else{
            context.status(400);
        }
    }

    private void getAllMessagesHandler(Context context) throws JsonProcessingException{
        List<Message> messages = MessageService.getAllMessages();
        context.json(messages);
        context.status(200);
    }
}