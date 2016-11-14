package de.toadette.poc.rtbm.domain;


import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class KeepJacocoTest {

    @Test
    public void keepme() throws Exception {
        String string="test";
        assertThat(string,is("test"));

    }
}
