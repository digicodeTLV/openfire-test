CREATE  PROCEDURE `init_test_users_procedure`()
    READS SQL DATA
BEGIN

    DECLARE x INT ;

    SET x = 0;
    INSERT INTO ofuser (username, storedKey, serverKey, salt, iterations, plainPassword, encryptedPassword, name, email, creationDate, modificationDate) VALUES ('test1', 'eIJX2ZpzWSw/0jSIJwIFj33Rct0=', 'JAlxF4AfOhYGt13QPBfxT8GQOAc=', '8Kv1vNP9Z4IYqr84uoqy+9ODX2kddaH/', 4096, null, null, null, null, '001473767300002', '001473767300002');

    WHILE x  <= 30000 DO
      SET x = x+1;
      INSERT INTO ofuser  (
              username,
                    storedKey,
                    serverKey,
                    salt,
                    iterations,
                    plainPassword,
                    encryptedPassword,
                    name,
                    email,
                    creationDate,
                    modificationDate
            )
      SELECT
          concat(username,x),
          storedKey,
          serverKey,
          salt,
          iterations,
          plainPassword,
          encryptedPassword,
          name,
          email,
          creationDate,
          modificationDate
      FROM
          ofuser
      WHERE username = 'test1';

      -- update passwords - set as Admin password (prepare it to 123)
      UPDATE ofUser u
        CROSS JOIN  ( SELECT  storedKey, serverKey, salt, encryptedPassword from ofUser where username = 'admin') AS a
        SET u.storedKey = a.storedKey,
            u.serverKey = a.serverKey,
            u.salt = a.salt,
            u.encryptedPassword = a.encryptedPassword
      WHERE u.username LIKE 'test1%';

    END WHILE;

END