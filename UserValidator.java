public class UserValidator {
	private Crytographer cryptographer;
	public boolean checkPasswordAndInitializeSession(String username, String password) {
		User user = UserGateway.findByName(username);
		if (user != User.NULL) {
			String codedPhrase = user.getPhraseEncodedByPassword();
			String phrase = cryptographer.decrypt(codedPhrase, password);
			if ("Valid Password".equals(phrase)) {
				Session.initialize(); // side effect
				return true;
			}
		}
		return false;
	}
}