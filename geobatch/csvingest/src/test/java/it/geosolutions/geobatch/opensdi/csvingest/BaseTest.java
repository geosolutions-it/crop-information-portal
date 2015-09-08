/*
 *  Copyright (C) 2013 GeoSolutions S.A.S.
 *  http://www.geo-solutions.it
 * 
 *  GPLv3 + Classpath exception
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.geosolutions.geobatch.opensdi.csvingest;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.io.FilenameUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ETj (etj at geo-solutions.it)
 */
public abstract class BaseTest {

    private final Logger LOGGER;
    @Rule
    public TestName _testName = new TestName();
    private String testName;
    /**
     * @see please use #getTestDir()
     */
    private static File testDataDir = null;
    private File testClassDir;  // temp dir for Class test
    private File tempDir; // temp dir for single test (for the method)


    public BaseTest() {
        LOGGER = LoggerFactory.getLogger(getClass());

//        for (Map.Entry<Object, Object> entry : System.getProperties().entrySet()) {
//            LOGGER.info( entry.getKey() + " --> " + entry.getValue());
//        }

        File datadir = loadFile("./datadir");
        System.setProperty("GEOBATCH_DATA_DIR", datadir.getAbsolutePath());

    }

    public String getTestName() {
        return testName;
    }

    @Before
    public synchronized void baseTestBefore() {
        LOGGER.info("---------- ");
        LOGGER.info("---------- RUNNING TEST " + getClass().getSimpleName() + " :: " + _testName.getMethodName());
        LOGGER.info("---------- ");

        String className = this.getClass().getSimpleName();
        testClassDir = new File(getTestDataDir(), className);
        if (!testClassDir.exists()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Using test class dir " + testClassDir);
            }
            testClassDir.mkdir();
        }

        String testName = _testName.getMethodName();
        tempDir = new File(testClassDir, testName);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Using test case dir " + tempDir);
        }
    }

    protected synchronized File getTempDir() {
        if (!tempDir.exists()) { // create dir lazily
            tempDir.mkdir();
        }

        return tempDir;
    }

    private synchronized File getTestDataDir() {

        if (testDataDir != null) {
            return testDataDir;
        }

        String startDir = System.getProperty("buildDirectory");
        if (startDir == null) {
            LOGGER.warn("Property 'buildDirectory' is not defined");

            File f = loadFile(".");
            if (f == null || !f.exists()) {
                LOGGER.warn("Undefined current directory");

                throw new IllegalStateException("Could not find a valid current dir");
            }

            String fa = f.getParentFile().getAbsolutePath();

            if (!"target".equals(FilenameUtils.getBaseName(fa))) {
                LOGGER.warn("Can't use current dir " + fa);
                throw new IllegalStateException("Could not find a valid current dir");
            }

            startDir = fa;
        }

        testDataDir = new File(startDir, "test-data-" + System.currentTimeMillis());

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Using test dir " + testDataDir);
        }

        testDataDir.mkdir();
        return testDataDir;
    }

    protected File loadFile(String name) {
        try {
            URL url = this.getClass().getClassLoader().getResource(name);
            if (url == null) {
                throw new IllegalArgumentException("Cant get file '" + name + "'");
            }
            File file = new File(url.toURI());
            return file;
        } catch (URISyntaxException e) {
            LOGGER.error("Can't load file " + name + ": " + e.getMessage(), e);
            return null;
        }
    }
    
}
