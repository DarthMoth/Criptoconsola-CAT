package CriptoConsolaCAT;

import java.io.*;
import java.nio.file.*;
import java.security.*;
import java.util.Scanner;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class Criptoconsola {

	private static String rutaActual = null;
	private static String prompt = null;
	private static String nomArxiu = null;

	public static void main(String[] args) {
		String clauSimetrica=null;

		MostrarTituloAplicacion();

		DefinirRutaActual();

		boolean tancarPrograma = false;
		tancarPrograma = false;

		while (!tancarPrograma) {

			FormarPrompt();

			InputStreamReader inputStreamReader = new InputStreamReader(System.in);
			BufferedReader inputBufferReader = new BufferedReader(inputStreamReader);

			try {

				String[] paraulesComandaUsuari = inputBufferReader.readLine().split(" ");
				String comandaUsuari = paraulesComandaUsuari[0].toUpperCase();

				switch (comandaUsuari) {

				case "SORTIR":
					System.out.println("\nSortint de l'aplicació...");
					tancarPrograma = true;
					break;
				case "AJUDA":
					ComandaAjuda();
					break;

				case "DIR":
				case "LS":
					ComandaDir();
					break;

				case "CD":
					ComandaCD(paraulesComandaUsuari);
					break;

				case "HASH":
					CrearHash(paraulesComandaUsuari);
					break;

				case "ENCRIPTARAES":
					Encriptar(paraulesComandaUsuari, clauSimetrica);
					break;
				case "DESENCRIPTARAES":
					Desencriptar(paraulesComandaUsuari, clauSimetrica);
					break;

				default:
					System.out.println("Comanda no disponible.\n");
					System.out.println("Escriu 'ajuda' per saber les comandes disponibles");
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private static void Desencriptar(String[] paraulesComandaUsuari, String clauStr) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Introdueix la clau de 16 caracters");
		clauStr = sc.nextLine();
		if (clauStr.length() == 16) {

			Key clauSimetrica = new SecretKeySpec(clauStr.getBytes(), "AES");
			Cipher cipher;
			String dadesEncriptadesEnString = null;

			try {

				cipher = Cipher.getInstance("AES");
				cipher.init(Cipher.DECRYPT_MODE, clauSimetrica);
				// System.out.println(paraulesComandaUsuari[1]);

				byte[] arxiuEnBytes = Files.readAllBytes(Paths.get(rutaActual + "/" + paraulesComandaUsuari[1]));
				byte[] arxiuEncriptarEnBytes = cipher.doFinal(arxiuEnBytes);

				String[] nomExtensio = paraulesComandaUsuari[1].split("\\.");
				FileOutputStream fos = new FileOutputStream(
				rutaActual + "/" + nomExtensio[0] + "(desxifrat)." + nomExtensio[1]);
				fos.write(arxiuEncriptarEnBytes);
				
				System.out.println("Arxiu Desencriptat correctament");
			} catch (IOException e) {

				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("Clau fora de rang");
		}
	}

	private static void Encriptar(String[] paraulesComandaUsuari, String clauStr) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Introdueix una clau de 16 caracters");
		clauStr = sc.nextLine();
		if (clauStr.length() == 16) {

			// Generar clau i cipher
			Key clauSimetrica = new SecretKeySpec(clauStr.getBytes(), "AES");
			Cipher cipher;
			String dadesEncriptadesEnString = null;

			try {
				// Cipher en mode encriptar
				cipher = Cipher.getInstance("AES");
				cipher.init(Cipher.ENCRYPT_MODE, clauSimetrica);

				byte[] arxiuEnBytes = Files.readAllBytes(Paths.get(rutaActual + "/" + paraulesComandaUsuari[1]));
				byte[] arxiuEncriptarEnBytes = cipher.doFinal(arxiuEnBytes);

				String[] nomExtensio = paraulesComandaUsuari[1].split("\\.");
				FileOutputStream fos = new FileOutputStream(
						rutaActual + "/" + nomExtensio[0] + "-xifrat." + nomExtensio[1]);
				fos.write(arxiuEncriptarEnBytes);
				System.out.println("Arxiu encriptat");
			} catch (IOException e) {

				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Clau fora de rang");
		}
	}

	private static String CrearHash(String[] paraulesComandaUsuari) {
		String hashResultantString = null;
		try {
			byte[] arxiuEnBytes = Files.readAllBytes(Paths.get(rutaActual + "/" + paraulesComandaUsuari[1]));

			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

			messageDigest.update(arxiuEnBytes);

			byte[] resultathashbytes = messageDigest.digest();

			hashResultantString = DatatypeConverter.printHexBinary(resultathashbytes);

			System.out.println(hashResultantString);

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hashResultantString;
	}

	private static void ComandaCD(String[] paraulesComandaUsuari) {

		if (!NombreParamsCorrecte(paraulesComandaUsuari, 2))
			return;
		String nouDirectori = paraulesComandaUsuari[1];

		if (nouDirectori.equals("..")) {

			File carpetaActual = new File(rutaActual);

			if (carpetaActual.getParent() != null) {

				rutaActual = carpetaActual.getParent();
			} else {// ja en la carpeta arrel.
				System.out.println("Ja estas a la carpeta arrel.");
			}

		} else {// volem entrar a una altre carpeta

			if (Files.exists(Paths.get(rutaActual + "/" + nouDirectori))) {

				rutaActual = rutaActual + "/" + nouDirectori;
			} else {
				System.out.println("El directori especificat no existeix");
			}
		}
	}

	private static boolean NombreParamsCorrecte(String[] paraulesComandaUsuari, int nombreParams) {

		if (paraulesComandaUsuari.length != 2) {
			System.out.println("ERROR: nombre de parametres incorrecte (la funcio nomes acepta " + nombreParams + ").");
			return false;
		}
		return true;
	}

	private static void ComandaDir() {

		File directoriActual = new File(rutaActual);
		File[] contingutDirectoriActual = directoriActual.listFiles();

		System.out.println("Contingut del directori " + directoriActual.toString());

		for (int i = 0; i < directoriActual.length(); i++) {
			System.out.println(contingutDirectoriActual[i]);
		}

	}

	private static void ComandaAjuda() {

		System.out.println("\nComanda AJUDA de l'aplicació: \n");
		
		System.out.println("AJUDA");
		System.out.println("\tMostra el contingut de l'ajuda del programa.\n");


		System.out.println("ENCRIPTARAES [nom del arxiu]");
		System.out.println("\tEncripta l'arxiu.\n");

		System.out.println("DESENCRIPTARAES [nom del arxiu]");
		System.out.println("\tDesencripta l'arxiu.\n");

		System.out.println("HASH");
		System.out.println("\tGuarda dades en HASH\n");

		System.out.println("DIR");
		System.out.println("\tMosta la ruta en la que et trobes.\n");

		System.out.println("CD [ruta]");
		System.out.println("\tMoure's per els directoris\n");

		System.out.println("LS");
		System.out.println("\tLlista els directoris\n");

		System.out.println("SORTIR");
		System.out.println("\tTanca l'aplicació.\n");
	}

	private static void FormarPrompt() {

		switch (System.getProperty("os.name")) {

		case "Windows 10":
			prompt = rutaActual + ">";
			break;

		case "Mac OS X":
		case "Linux":
			prompt = rutaActual + "$ ";
			break;
		}
		System.out.print(prompt);
	}

	private static void DefinirRutaActual() {

		System.out.println("Sistema operatiu: " + System.getProperty("os.name") + "\n");

		switch (System.getProperty("os.name")) {

		case "Windows":
			// rutaActual = System.getenv("SystemDrive");
			rutaActual = "E:\\";

			break;

		case "Mac OS X":
			Path root = Paths.get(System.getProperty("user.dir")).getFileSystem().getRootDirectories().iterator()
					.next();
			rutaActual = root.toString() + "Users/user/PSP";
			break;

		case "Linux":
			Path root2 = Paths.get(System.getProperty("user.dir")).getFileSystem().getRootDirectories().iterator()
					.next();
			rutaActual = root2.toString() + "/Users/user";
			break;
		}
	}

	private static void MostrarTituloAplicacion() {

		System.out.println("===========================");
		System.out.println("*******Criptoconsola*******");
		System.out.println("===========================");

	}

}
