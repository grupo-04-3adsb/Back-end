package tcatelie.microservice.auth.util;

import java.text.Normalizer;

public class StringUtilsHelp {

  public static String formatarNomeArquivo(String nomeOriginal) {
    String nomeSemAcentos = Normalizer.normalize(nomeOriginal, Normalizer.Form.NFD)
            .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

    return nomeSemAcentos
            .replaceAll("[^a-zA-Z0-9\\.\\-]", "_")
            .replaceAll("_+", "_");
  }

}
