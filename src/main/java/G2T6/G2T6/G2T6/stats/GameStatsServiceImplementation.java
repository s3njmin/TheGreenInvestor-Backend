//package G2T6.G2T6.G2T6.stats;
//
//import org.springframework.stereotype.Service;
//
//import java.awt.print.Book;
//import java.util.List;
//
//@Service
//public class GameStatsServiceImplementation implements GameStatsService{
//    private JdbcGameStatsRepository stats;
//    public GameStatsServiceImplementation(JdbcGameStatsRepository stats){
//        this.stats = stats;
//    }
//    @Override
//    public List<GameStats> listGameStats() {
//        return stats.listGameStats();
//    }
//
//    @Override
//    public GameStats getGameStats(Long id) {
//        return null;
//    }
//
//    @Override
//    public GameStats addGameStats(GameStats book) {
//        return null;
//    }
//
//    @Override
//    public GameStats updateGameStats(Long id, GameStats book) {
//        return null;
//    }
//
//    @Override
//    public void deleteGameStats(Long id) {
//
//    }
////
////
////    public BookServiceImpl(BookRepository books){
////        this.books = books
////    }
////
////    @Override
////    public List<Book> listBooks() {
////        return books.findAll();
////    }
////
////
////    @Override
////    public Book getBook(Long id){
////        return books.findById(id).orElse(null);
////    }
////
////    @Override
////    public Book addBook(Book book) {
////        return books.save(book);
////    }
////
////    @Override
////    public Book updateBook(Long id, Book newBookInfo){
////        return books.findById(id).map(book -> {book.setTitle(newBookInfo.getTitle());
////            return books.save(book);
////        }).orElse(null);
////
////    }
////
////    /**
////     * Remove a book with the given id
////     * Spring Data JPA does not return a value for delete operation
////     * Cascading: removing a book will also remove all its associated reviews
////     */
////    @Override
////    public void deleteBook(Long id){
////        books.deleteById(id);
////    }
//}
