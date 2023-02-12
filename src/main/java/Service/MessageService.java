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
        return getMessageByID(message);
    }
}
