package org.recap.camel;

import com.sheik.app.camel.RecapCamelContext;
import org.apache.camel.CamelContext;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by SheikS on 6/20/2016.
 */
public class CamelProcessingTest {

    @Test
    public void processTest() {
        RecapCamelContext recapCamelContext = RecapCamelContext.getInstance();
        assertNotNull(recapCamelContext);
        String tempLocation = System.getProperty("java.io.tmpdir");
        String bulkFileLocationPath = tempLocation + File.separator + "FileToLoad";
        String fileLocationPath = getFilePath("records/bibRecord-Test.xml");
        assertNotNull(fileLocationPath);
        File sourceFileLocation = new File(fileLocationPath);
        assertTrue(sourceFileLocation.exists());

        File bulkFileLocation = null;
        try {
            if(StringUtils.isNotBlank(bulkFileLocationPath)) {
                bulkFileLocation = new File(bulkFileLocationPath);
                if (!bulkFileLocation.exists() || !bulkFileLocation.isDirectory()) {
                    bulkFileLocation.mkdirs();
                }
                org.apache.commons.io.FileUtils.copyFileToDirectory(sourceFileLocation, bulkFileLocation,false);

                CamelContext context = recapCamelContext.getContext();
                context.start();
                recapCamelContext.addDynamicBibRecordStreamRoute(recapCamelContext, bulkFileLocation.getAbsolutePath(), 5);
                Thread.sleep(3000);
                context.stop();

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(null != bulkFileLocation) {
                bulkFileLocation.deleteOnExit();
            }
        }

    }

    public static String getFilePath(String classpathRelativePath)  {
        try {
            Resource rsrc = new ClassPathResource(classpathRelativePath);
            return rsrc.getFile().getAbsolutePath();
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
