package id.ac.ui.cs.advprog.eshop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EshopApplicationTests {

    @Test
    void testMain() {
        String[] args = {};
        assertDoesNotThrow(() -> EshopApplication.main(args));
    }
}