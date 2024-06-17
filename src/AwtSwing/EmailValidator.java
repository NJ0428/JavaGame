import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {
    
    // 이메일 패턴 정규식
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    
    // 이메일 검증 메서드
    public static boolean validateEmail(String email) {
        // 이메일 패턴을 컴파일
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        
        // 이메일 패턴에 맞는지 확인
        if (!matcher.matches()) {
            return false;
        }
        
        // @ 기호를 기준으로 아이디와 도메인을 분리
        String[] parts = email.split("@");
        if (parts.length != 2) {
            return false;
        }
        
        String id = parts[0]; // 이메일 아이디 부분
        String domain = parts[1]; // 도메인 부분
        
        // 아이디 길이가 8에서 15 사이인지 확인
        if (id.length() < 8 || id.length() > 15) {
            return false;
        }
        
        // 아이디에 숫자와 대문자가 적어도 한 개씩 있는지 확인
        boolean hasNumber = false;
        boolean hasUpperCase = false;
        for (char c : id.toCharArray()) {
            if (Character.isDigit(c)) {
                hasNumber = true;
            } else if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            }
        }
        
        // 숫자와 대문자가 적어도 한 개씩 있는지 확인
        if (!hasNumber || !hasUpperCase) {
            return false;
        }
        
        return true;
    }
    
    // 테스트를 위한 main 메서드
    public static void main(String[] args) {
        
    	// 테스트할 이메일
        String email ="test@example.com";
        validateEmail(email);
    }
}
