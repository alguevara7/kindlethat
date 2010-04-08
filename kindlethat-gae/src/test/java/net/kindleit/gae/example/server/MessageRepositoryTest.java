package net.kindleit.gae.example.server;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;

import net.alexguev.kindlethat.model.Message;
import net.alexguev.kindlethat.repository.MessageRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Tests {@link MessageRepository} class.
 * 
 * @author androns
 */
@Ignore
public class MessageRepositoryTest extends LocalDatastoreTest {

    private MessageRepository messageRepository;

    /**
     * @see LocalDatastoreTest#setUp()
     */
    @Before
    @Override
    public void setUp() {
        super.setUp();
        this.messageRepository = new MessageRepository();
    }

    /**
     * @see LocalDatastoreTest#tearDown()
     */
    @After
    @Override
    public void tearDown() {
        super.tearDown();
    }

    /**
     * 
     */
    @Test
    public void smokeTest() {
        assertNotNull(this.messageRepository);

        // create
        Message message = new Message();
        message.setText("Test message");

        this.messageRepository.create(message);

        // read
        Collection<Message> messages = this.messageRepository.getAll();

        assertNotNull(messages);
        assertEquals(1, messages.size());
        Message storedMessage = messages.iterator().next();

        assertNotNull(storedMessage.getId());
        assertEquals(message.getText(), storedMessage.getText());

        // delete
        this.messageRepository.deleteById(storedMessage.getId());

        messages = this.messageRepository.getAll();
        assertEquals(0, messages.size());
    }
}
