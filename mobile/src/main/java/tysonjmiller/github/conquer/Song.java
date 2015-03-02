package tysonjmiller.github.conquer;

import java.io.Serializable;

/**
 * Created by Tyson Miller on 3/1/2015.
 */
public class Song implements Serializable {
    public String title;
    public String artist;
    public boolean isLocal;
    public String localUri;
    public String remoteUrl;
    public String filePath;
    public String album;
    public String duration;
}
