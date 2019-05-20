package serializer;

import com.google.gson.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Le rôle de cette classe est de serializer les objets Java et de les écrire dans des fichiers textes type json.
 */
public class JSONSerializer {
	Gson gson;

	private static BufferedWriter write;

	/**
	 * Appel au module gson pour créer du json.
	 */
	public JSONSerializer() {
		GsonBuilder builder = new GsonBuilder().setPrettyPrinting().serializeNulls()
				.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE);
		this.gson = builder.create();
	}

	/**
	 * @param jtmp Le template est la structure du dictionnaire json
	 * @throws IOException exception
	 */
	public void serializeToFile(JSONTemplate jtmp) throws IOException {
		File jsonOutput = new File(jtmp.filename);
		try {
			FileWriter writer = new FileWriter(jsonOutput);
			writer.write(this.gson.toJson(jtmp));

			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Surcharge dd la méthode serializeToFile pour l'adapter à un autre type de template (celui du personnel).
	 * @param jtmp template json
	 * @throws IOException exception
	 */
	public void serializeToFile(JSONListePersonnelTemplate jtmp) throws IOException {
		File jsonOutput = new File(jtmp.filename);
		try {
			FileWriter writer = new FileWriter(jsonOutput);
			writer.write(this.gson.toJson(jtmp));

			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Méthode de test du module gson.
	 * @param x liste d'objets
	 */
	public void printJson(ArrayList x) {
		System.out.println(this.gson.toJson(x));
	}

}