/*******************************************************************************
 * Copyright (c) 2010, 2012 Institute for Dutch Lexicology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package nl.inl.blacklab.forwardindex;

import java.io.File;
import java.util.Arrays;

import junit.framework.Assert;
import nl.inl.util.Utilities;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestForwardIndex {
	private ForwardIndex fi;

	private File dir;

	String[][] str = { { "How", "much", "wood" }, { "would", "a", "woodchuck", "chuck" },
			{ "if", "a", "woodchuck", "could", "chuck", "wood" } };

	@Before
	public void setUp() {

		// Remove any previously left over temp test dirs
		Utilities.removeBlackLabTestDirs();

		// Create new test dir
		dir = Utilities.createBlackLabTestDir("ForwardIndex");

		fi = new ForwardIndex(dir, true, true);
		try {
			// Store strings
			for (int i = 0; i < str.length; i++) {
				Assert.assertEquals(i, fi.addDocument(Arrays.asList(str[i])));
			}
		} finally {
			fi.close(); // close so everything is guaranteed to be written
		}
		fi = new ForwardIndex(dir);
	}

	@After
	public void tearDown() {
		if (fi != null)
			fi.close();
		// Try to remove (some files may be locked though)
		Utilities.removeBlackLabTestDirs();
	}

	@Test
	public void testRetrieve() {
		// Retrieve strings
		for (int i = 0; i < str.length; i++) {
			Assert.assertEquals(Arrays.asList(str[i]), Arrays.asList(fi.retrievePart(i, -1, -1)));
		}
	}

}
