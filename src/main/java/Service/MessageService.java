package Service;
import DAO.MessageDAO;
import Model.Message;
import java.util.List;

public class MessageService {

    public MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public Message createMessage(Message message){
        return messageDAO.createMessage(message);
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message getMessageByID(int message){
        return messageDAO.getMessageByID(message);
    }

    public Message deleteMessageByID(int message){
        Message messageId = messageDAO.getMessageByID(message);
        messageDAO.deleteMessageByID(message);

        if(messageId != null){
            return messageId;
        }
        else{
            return messageDAO.deleteMessageByID(message);
        }
    }

    public Message updateMessage(int messageId, Message message){
        return messageDAO.updateMessage(messageId, message);
    }
    
    public List<Message> getMessagesByAccount(int accountId){
        return messageDAO.getMessagesByAccount(accountId);
    }
}
