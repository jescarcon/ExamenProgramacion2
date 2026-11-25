package examen;

import java.util.Scanner;

class ParkingGratisException extends Exception {
	public ParkingGratisException(String mensaje) {
		super(mensaje);
	}
}

public class Garaje {

	public static final Integer TOTAL_PLAZAS = 2700;

	public static void main(String[] args){

//		System.out.println(FormateaMatricula("  1234abc "));
//		System.out.println(ValidaMatricula(FormateaMatricula("  1234abc ")));
//		System.out.println(PlazasLibres(100));
//		System.out.println(SumaDigitos("1234abc"));
//		System.out.println(AsignaPlaza("1234ABC"));

//		CompruebaPromocion("1234ABC");
//		CompruebaPromocion("5555ABC");
//		CompruebaPromocion("1234DAW");

//		System.out.println(GeneraCodigoAcceso(new StringBuilder("1234ABC")));

//		System.out.println(EsCapicua("1234ABC"));
//		System.out.println(EsCapicua("2332ABC"));

		Scanner sc = new Scanner(System.in);
		Integer plazasOcupadas = 1;
		String matricula = "";
		System.out.println("==== Bienvenido al Parking ======");

		if(PlazasLibres(plazasOcupadas)>0) {

			do {
				System.out.printf("Plazas libres: %d\n",PlazasLibres(plazasOcupadas));

				System.out.print("Introduzca su matrícula (o fin para terminar) ej->1234abc :");
				matricula = sc.next();
				
				if (!matricula.trim().toLowerCase().equals("fin") && PlazasLibres(plazasOcupadas) > 0) {

					matricula = FormateaMatricula(matricula);
					System.out.printf("\nMatrícula formateada: %s\n", FormateaMatricula(matricula));
					if( ValidaMatricula(matricula)) {
						System.out.println("Matrícula Válida.");
						try {
							
							CompruebaPromocion(matricula);

						}catch(ParkingGratisException e) {
							System.out.println(e.getMessage());
						}
						System.out.printf("\n%s\n", EsCapicua(matricula) ? "¡La parte numérica de la matrícula es capicúa!"	: "La parte numérica de la matrícula no es capicúa.");
						System.out.printf("Código de acceso: %s\n",GeneraCodigoAcceso(new StringBuilder(matricula)));
						System.out.printf("Plaza asignada: %s\n\n",AsignaPlaza(matricula));
						plazasOcupadas++;
					}else {
						System.out.println("Error. matrícula no válida.");
					}
					
				}

			} while (!matricula.trim().toLowerCase().equals("fin") && PlazasLibres(plazasOcupadas) > 0); 
			System.out.printf("\nPrograma finalizado por indicación de fin o por falta de plazas.",PlazasLibres(plazasOcupadas));
			
		}else {
			System.out.println("No hay plazas libres.");
		}
		sc.close();
	}

	// 1) Función elimina espacios y convierte a mayúscula.
	private static String FormateaMatricula(String matricula) {
		return matricula.trim().toUpperCase();
	}

	// 2) Función que valida patrón de normativa europea
	private static Boolean ValidaMatricula(String matricula) {
		// Sigue patrón 1234ABC
		return matricula.matches("\\d{4}[A-Z]{3}");
	}

	// 3) Función que calcula las plazas libres (del total global) dada las ocupadas
	private static int PlazasLibres(Integer plazasActualmenteOcupadas) {
		return TOTAL_PLAZAS - plazasActualmenteOcupadas;
	}

	// 4) Devuelve la suma de los digitos 1234ABC -> 10
	private static Integer SumaDigitos(String matricula) {
		// suponemos matricula limpia de espacios
		Integer suma = 0;
		for (int i = 0; i < 4; i++) {
			int digito = Integer.parseInt(String.valueOf(matricula.charAt(i)));
			suma += digito;
		}
		return suma;
	}

	// 5) Declara la plaza como la concatenación de la última letra y la suma de los
	// dígitos ej: 1234ABC -> C10
	private static String AsignaPlaza(String matricula) {
		// Supongo matricula ya formateada
		return String.format("%s%d", String.valueOf(matricula.charAt(matricula.length() - 1)), SumaDigitos(matricula));
	}

	// 6) Lanza excepcion si suma >=20 o letras == DAW
	private static void CompruebaPromocion(String matricula) throws ParkingGratisException {
		if (SumaDigitos(matricula) >= 20 || matricula.substring(4, 7).equals("DAW")) {
			throw new ParkingGratisException("Vehículo agraciado con aparcamiento gratuíto.");
		}
	}

	// 7) Genera codigo a partir de Matricula: 1234ABC -> CBA-10
	private static String GeneraCodigoAcceso(StringBuilder matricula) {

		Integer sumaDigito = SumaDigitos(matricula.toString()); // suma de los digitos
		String letrasReves = matricula.reverse().substring(0, 3); // letras al reves

		return String.format("%s-%d", letrasReves, sumaDigito);
	}

	// 8) Capicua para los digitos ej: 2332 true y 1234 false
	private static Boolean EsCapicua(String matricula) {
		// supongo parseado previo
		// Uso stringbuilder para darle la vuelta con reverse y comparo con equals el
		// original,
		// ojo con la referencia en memoria que stringbuilder si lo modificas y lo
		// reutilizas se queda reversed.
		return new StringBuilder(matricula.substring(0, 4)).reverse().toString().equals(matricula.substring(0, 4));
	}

}
