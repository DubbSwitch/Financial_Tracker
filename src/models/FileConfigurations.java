package models;

import java.io.Serializable;
import java.nio.file.Path;

//TODO: IF YOU MODIFY THIS CLASS IN ANY WAY, YOU MUST UPDATE serialVersionUID TO A VALID SERIAL VERSION ACROSS ALL SERIALIZABLE CLASSES!!

public class FileConfigurations implements Serializable {
    private static final long serialVersionUID = 4L;
    private Path path; // Path to dir where data is stored.

    public void setPath(Path path) {
        this.path = path;
    }

    public Path getPath() {
        return path;
    }
}
