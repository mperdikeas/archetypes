package _int.esa.esavo.tapservlet.regtap;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.matchers.JUnitMatchers.both;
import static org.junit.matchers.JUnitMatchers.containsString;
import static org.junit.matchers.JUnitMatchers.everyItem;
import static org.junit.matchers.JUnitMatchers.hasItems;

import java.util.List;
import java.util.ArrayList;

/**
 * Tests for {@link dummy}.
 *
 * @author mperdikeas@sciops.esa.int
 */
public class DummyTest {

    @Test
    public void thisAlwaysPasses() {
    }

    @Test
    @Ignore
    public void thisIsIgnored() {
    }

    @Test
    public void demonstrateThatClassesAreVisible() {
        RegTAPADQLQueryFactory  x = null;
    }


}
