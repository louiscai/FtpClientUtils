/**
 * 
 */
package util;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;


/**
 * @author Cairuzhang
 * @date 2015-12-29
 *
 */
public class FtpUtils {
    public static FTPClient getFtpClient(String dir) throws Exception {
        FTPClient client = new FTPClient();
        client.connect("host", 0);
        if (!client.login("account", "password")) {
            throw new Exception("fail to login with " + "${account}");
        }

        client.enterLocalPassiveMode();
        client.setFileType(FTP.BINARY_FILE_TYPE);

        if (!client.changeWorkingDirectory(dir)) {
            throw new Exception("操作失败，FTP进入目录失败");
        }

        return client;
    }
    
    // 建立多层FTP目录，FTPClient只允许一层一层建立目录
    public static boolean makeDirsAndChangeDirs(FTPClient ftpClient,
            String dirPath) throws Exception {
        String[] pathElements = dirPath.split("/");
        if (pathElements != null && pathElements.length > 0) {
            for (String singleDir: pathElements) {
                boolean existed = ftpClient.changeWorkingDirectory(singleDir);
                if (!existed) {
                    boolean created = ftpClient.makeDirectory(singleDir);
                    if (created) {
                        ftpClient.changeWorkingDirectory(singleDir);
                    } else {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
