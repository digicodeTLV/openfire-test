CREATE  PROCEDURE `init_test_users_procedure`()
    READS SQL DATA
BEGIN

    DECLARE x INT ;

    SET x = 0;
    INSERT INTO ofuser (username, storedKey, serverKey, salt, iterations, plainPassword, encryptedPassword, name, email, creationDate, modificationDate) VALUES ('test1', 'eIJX2ZpzWSw/0jSIJwIFj33Rct0=', 'JAlxF4AfOhYGt13QPBfxT8GQOAc=', '8Kv1vNP9Z4IYqr84uoqy+9ODX2kddaH/', 4096, null, null, null, null, '001473767300002', '001473767300002');
    -- password is `123`

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
                where username = 'test1';

    END WHILE;

END