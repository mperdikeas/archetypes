package a.b.c;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized.Parameter;
import org.junit.BeforeClass; 


import static org.junit.Assume.assumeTrue;
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
import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;
import java.net.URL;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.collections.IteratorUtils;






@RunWith(Parameterized.class)
public class FileTest {

    private static final File testFilesDir;

    static {
        URL url = FileTest.class.getResource("/test-files");
        testFilesDir = new File(url.getFile());
    }

    private static String targetFriendlyName;
    @BeforeClass
    public static void assignTargeted() {
        targetFriendlyName = System.getProperty("target-friendly-name");
        System.out.printf("\n\n\n\n\n\n\n\n********************** %s ****************\n\n\n\n\n\n", targetFriendlyName);
    }
    

    @Parameters(name= "{index}: {1}")
    public static Collection<Object[]> data() {
        Collection<File> _rv = IteratorUtils.toList( FileUtils.iterateFiles(testFilesDir, TrueFileFilter.INSTANCE, (IOFileFilter) null) );
        Collection<Object[]> rv = new ArrayList<>();
        for (File f : _rv)
            rv.add(new Object[]{f, f.getName()});
        return rv;
    }

    private final File   testFile;
    private final String friendlyTestName;

    public FileTest(File testFile, String friendlyTestName) {
        this.testFile = testFile;
        this.friendlyTestName = friendlyTestName;
    }



    @Test
    public void testFile() {
        assumeTrue( (targetFriendlyName==null)||(targetFriendlyName.equals(friendlyTestName)) );
        D d = new D();
        System.out.printf("testing [%s]\n", testFile.getPath());
        if (testFile.getName().startsWith("x")) {
            try {
                d.handle( testFile );
                // if we failed to throw exception add error
                Assert.fail( String.format("File %s failed to cause exception", testFile.getName()) );
            } catch (RuntimeException e) {
                // this is expected do nothing
            }
        } else {
            d.handle( testFile );
        }
    }
}


