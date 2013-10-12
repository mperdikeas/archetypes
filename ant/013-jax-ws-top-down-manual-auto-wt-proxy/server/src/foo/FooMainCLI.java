package foo;

import com.beust.jcommander.Parameter;
import _int.esa.esavo.common.BaseDBCLI;

public class FooMainCLI {
    /*
    @Parameter(names = {"-ivoid", "--repo-ivoid"}, description="IVOA identifier of repo for which ingestion is performed", required=true)
    public String repoIvoid;

    @Parameter(names = {"-prefix", "--metadata-prefix"}, description="IVOA identifier of repo for which ingestion is performed", required=true)
    public String prefix;

    @Parameter(names = {"-d", "--full-harvest-dir-to-ingest"}, description="file-path to the directory of successful full harvest from which to ingest resources", required=true)
    public String dirPath;*/

    @Parameter(names = {"-wsimport", "--use-wsimport-toolchain"}, description="use wsimport-generated artifacts for the server (0 : false, 1 : true)", required=true)
    public int wsimport;

    /*    @Parameter(names = {"-h", "--help"}, description="help", help=true)
          public boolean help = false;*/

}
