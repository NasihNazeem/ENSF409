public class RegistrationApp {
	public static void main (String[] args) {
		CourseCatalogue cat = new CourseCatalogue ();
        var outgoingMenu = new OutgoingMenu(9898, cat);
        outgoingMenu.relay(cat);
	}
}