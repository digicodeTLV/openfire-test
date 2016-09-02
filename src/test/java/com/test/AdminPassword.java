package com.test;

import lombok.extern.slf4j.Slf4j;
import org.jivesoftware.util.Blowfish;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by tymoshenkol on 02-Sep-16.
 */
@Slf4j
public class AdminPassword {
	@Test
	public void passwordEncryption () {

		/*--------- Cannot Login to Admin Console? -------------------------------------------------------------*/


		/*  0. Define new password:*/
		String plainPass = "qwert123q";
		/*  1.  SELECT propValue FROM ofProperty WHERE name='passwordKey';
		        -- result is: 'LvYKD7aoFYb9y95'
	    */
		String passwordKey = "LvYKD7aoFYb9y95";
		/*  2. Create encrypted password row:*/
		Blowfish cipher = new Blowfish(passwordKey);
		String encrypted = cipher.encryptString(plainPass);
		log.debug("password={}; encrypted={}", encrypted);
		/*  -- result is: encrypted=679b..blah-blah-blah...e5a  (needed for Step#3) */
		Assert.assertEquals("679b3f2cdbd9aafc2ff9300daa9273d3c1442fcb66a7c9f299903945d9d17e5a", encrypted);
		/*  3.  INSERT INTO ofUser (username, encryptedPassword) values('newAdminUser', '679b.....e5a');
		        -- or
		        UPDATE ofUser SET encryptedPassword = '679b.....e5a' WHERE username = 'newAdminUser';
			4.  SELECT propValue FROM ofProperty WHERE name = 'xmpp.domain';
		        -- result is: 'example.com' (needed for Step#5)
			5.  UPDATE ofProperty up
		        SET propValue = concat(propValue, ',newAdminUser@example.com')
		        WHERE name='admin.authorizedJIDs'
		*/

	}
}
