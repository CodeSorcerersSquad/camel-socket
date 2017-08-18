package br.com.code.sorcerers.routes;

/**
 * @author victor
 *
 */

public class RoutesCreation {

    public static String[] pathCreation(String adressess) {
        // Pega a as URIS e separa em um array dividido por virgula
        String[] uris = adressess.split(",");//getContext().resolvePropertyPlaceholders("{{addresses}}").split(",");
        String[] nettyList = new String[uris.length];

        // Itera o array de URIS para poder montar os caminhos desejados de socket
        for(int i = 0; i < uris.length; i++) {
            StringBuilder path = new StringBuilder("netty4-http:http://");
            path.append(uris[i]);
            nettyList[i] = path.toString();
        }
        return nettyList;
    }
}
