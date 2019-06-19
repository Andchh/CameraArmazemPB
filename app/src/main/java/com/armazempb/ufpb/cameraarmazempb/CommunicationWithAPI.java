package com.armazempb.ufpb.cameraarmazempb;

import java.io.File;

/**
 *     Describes an way to access an API that it's used in the project.
 * P.S.: this interface get a bit concrete by fixing only to this project, but whatever.
 * */
public interface CommunicationWithAPI {

    /**
     *      Send File describe a method that must to communicate with some API, be able to
     * to send a file and return if the operation was sucessful
     *
     * @param file A link to where the method must get the file.
     *
     * @return if the operation was sucessful
     * */
    public boolean sendFile(File file);
}
