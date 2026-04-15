import javax.swing.*;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Message {
    private String sender;
    private String text;

    public Message(String sender, String text) {
        this.sender = sender;
        this.text = text;
    }

    public String format() {
        return sender + ": " + text;
    }
}

interface ResponseStrategy {
    boolean canHandle(String input);
    String getResponse(String input);
}

class GreetingStrategy implements ResponseStrategy {
    public boolean canHandle(String input) {
        return input.contains("hello") || input.contains("hi");
    }

    public String getResponse(String input) {
        return random("Hi 👋", "Hello!", "Hey there!");
    }

    private String random(String... r) {
        return r[new Random().nextInt(r.length)];
    }
}

class JokeStrategy implements ResponseStrategy {
    public boolean canHandle(String input) {
        return input.contains("joke");
    }

    public String getResponse(String input) {
        return random(
            "Why Java? Because it's class-y 😎",
            "Debugging = detective work 🕵️",
            "I threw an exception... nobody caught it 😂"
        );
    }

    private String random(String... r) {
        return r[new Random().nextInt(r.length)];
    }
}

class DefaultStrategy implements ResponseStrategy {
    public boolean canHandle(String input) {
        return true;
    }

    public String getResponse(String input) {
        return random("Interesting", "Tell me more!", "Okay 👍");
    }

    private String random(String... r) {
        return r[new Random().nextInt(r.length)];
    }
}

class ChatBot {
    private List<ResponseStrategy> strategies = new ArrayList<>();

    public ChatBot() {
        strategies.add(new GreetingStrategy());
        strategies.add(new JokeStrategy());
        strategies.add(new DefaultStrategy());
    }

    public String getReply(String input) {
        input = input.toLowerCase();
        for (ResponseStrategy s : strategies) {
            if (s.canHandle(input)) {
                return s.getResponse(input);
            }
        }
        return "Hmm...";
    }
}

interface ChatService {
    void send(Message msg);
}

class ChatServiceImpl implements ChatService {
    private ChatUI ui;

    public ChatServiceImpl(ChatUI ui) {
        this.ui = ui;
    }

    public void send(Message msg) {
        ui.display(msg.format());
    }
}

class ChatUI extends JFrame {
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendBtn;
    private ChatService service;
    private ChatBot bot;

    public ChatUI() {
        setTitle("Chat App (Swing + SOLID + Threads)");
        setSize(450, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(chatArea);

        inputField = new JTextField();
        sendBtn = new JButton("Send");

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(inputField, BorderLayout.CENTER);
        bottom.add(sendBtn, BorderLayout.EAST);

        add(scroll, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        bot = new ChatBot();
        service = new ChatServiceImpl(this);

        sendBtn.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage());

        setVisible(true);
    }

    public void display(String msg) {
        SwingUtilities.invokeLater(() -> {
            chatArea.append(msg + "\n");
        });
    }

    private void sendMessage() {
        String text = inputField.getText().trim();
        if (text.isEmpty()) return;

        Message userMsg = new Message("You", text);
        inputField.setText("");

        new Thread(() -> {
            service.send(userMsg);

            try {
                Thread.sleep(1000);
            } catch (Exception e) {}

            String reply = bot.getReply(text);
            Message botMsg = new Message("Bot", reply);
            service.send(botMsg);
        }).start();
    }
}

public class ChatApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChatUI::new);
    }
}
