
/*
- Придумать несколько взаимосвязанных классов,
в которых можно использовать три способа обработки исключений (вывод ошибки, rethrow и еще один, на выбор)
- А так же использована конструкция try-with-resource
- Вместо обращения к реальным ресурсам можно использовать классы-заглушки
- Результатом выполнения задания является код на гитхабе оформленный в виде pull request.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class Main {
    public static void main(String[] args) throws OperationIsImpossibleException {
        try {
            int a = 5 / 0;
            System.out.println(a);
        } catch (ArithmeticException ex) {
            System.out.println("\n Невозможно поделить на ноль!"); //простейший способ вывода информации при отлове ошибки
        }

        //вызываем метод, отлавливаем ошибку и выводим стэктрейс
        try (var br = new BufferedReader(new InputStreamReader(System.in));
             var cl = new Cl())
        { // пример использования конструкции try-with-resources
            getException(); //вызов метода с выбросом исключения
        } catch (ArrayIndexOutOfBoundsException | IOException e) { //отлов исключения
            Exception exception = new Exception("Array index out of bound"); //вывод стектрейса и сообщения к нему
            exception.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //rethrow
        try {
            try {
                try {
                    throw new CannotFindFileException();
                } catch (Exception e) {
                    throw new CannotReadFileException();
                }
            } catch (Exception e) {
                throw new OperationNotFoundException();
            }
        } catch (Exception e) {
            throw new OperationIsImpossibleException();
        }
    }
    private static void getException () {
        throw new ArrayIndexOutOfBoundsException();
    }
}

class Cl implements AutoCloseable {
    @Override
    public void close() throws Exception {
    }
}
//классы-заглушки
class OperationIsImpossibleException extends Exception {}
class OperationNotFoundException extends Exception {}
class CannotReadFileException extends Exception {}
class CannotFindFileException extends Exception {}

