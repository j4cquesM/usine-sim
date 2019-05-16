package serializer;

import com.google.gson.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public class JSONSerializer {
	Gson gson;

	private static BufferedWriter write;

	public JSONSerializer() {
		GsonBuilder builder = new GsonBuilder().setPrettyPrinting().serializeNulls()
				.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE);
		this.gson = builder.create();
	}

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

	public void printJson(ArrayList x) {
		System.out.println(this.gson.toJson(x));
	}

}
