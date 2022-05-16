import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Comparator;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.Before;

public class TestCases {
   private static final Song[] songs = new Song[] {
         new Song("Decemberists", "The Mariner's Revenge Song", 2005),
         new Song("Rogue Wave", "Love's Lost Guarantee", 2005),
         new Song("Avett Brothers", "Talk on Indolence", 2006),
         new Song("Gerry Rafferty", "Baker Street", 1998),
         new Song("City and Colour", "Sleeping Sickness", 2007),
         new Song("Foo Fighters", "Baker Street", 1997),
         new Song("Queen", "Bohemian Rhapsody", 1975),
         new Song("Gerry Rafferty", "Baker Street", 1978)
   };

   @Test
   public void testArtistComparator() {
      ArtistComparator comparator = new ArtistComparator();
      int result = comparator.compare(songs[0], songs[1]);
      assertTrue(result < 0);
   }

   @Test
   public void testArtistComparator1() {
      ArtistComparator comparator = new ArtistComparator();
      int result = comparator.compare(songs[0], songs[0]);
      assertTrue(result == 0);
   }

   @Test
   public void testArtistComparator2() {
      ArtistComparator comparator = new ArtistComparator();
      int result = comparator.compare(songs[0], songs[2]);
      assertTrue(result > 0);
   }

   @Test
   public void testLambdaTitleComparator() {
      Comparator<Song> comp = (s1, s2) -> s1.getTitle().compareTo(s2.getTitle());
      int result = comp.compare(songs[0], songs[1]);
      assertTrue(result > 0);
   }

   @Test
   public void testLambdaTitleComparator1() {
      Comparator<Song> comp = (s1, s2) -> s1.getTitle().compareTo(s2.getTitle());
      int result = comp.compare(songs[0], songs[0]);
      assertTrue(result == 0);
   }

   @Test
   public void testLambdaTitleComparator2() {
      Comparator<Song> comp = (s1, s2) -> s1.getTitle().compareTo(s2.getTitle());
      int result = comp.compare(songs[1], songs[4]);
      assertTrue(result < 0);
   }

   @Test
   public void testYearExtractorComparator() {
      Comparator<Song> comp = Comparator.comparingInt(Song::getYear).reversed();
      int result = comp.compare(songs[1], songs[2]);
      assertTrue(result > 0);
   }

   @Test
   public void testYearExtractorComparator1() {
      Comparator<Song> comp = Comparator.comparingInt(Song::getYear).reversed();
      int result = comp.compare(songs[0], songs[7]);
      assertTrue(result < 0);
   }

   @Test
   public void testYearExtractorComparator2() {
      Comparator<Song> comp = Comparator.comparingInt(Song::getYear).reversed();
      int result = comp.compare(songs[0], songs[1]);
      assertTrue(result == 0);
   }

   @Test
   public void testComposedComparator() {
      Comparator<Song> c1 = (s1, s2) -> s1.getTitle().compareTo(s2.getTitle());
      Comparator<Song> c2 = (s1, s2) -> (s1.getYear() - s2.getYear());
      Comparator<Song> comp = new ComposedComparator(c1, c2);

      int result = comp.compare(songs[3], songs[7]);
      assertTrue(result > 0); // greater because 1978 then 1998
   }

   @Test
   public void testThenComparing() {
      Comparator<Song> c1 = Comparator.comparing(Song::getTitle);
      Comparator<Song> c2 = c1.thenComparing(Song::getArtist);

      int result = c2.compare(songs[3], songs[5]);
      assertTrue(result > 0);
   }

   @Test
   public void runSort() {
      List<Song> songList = new ArrayList<>(Arrays.asList(songs));
      List<Song> expectedList = Arrays.asList(
            new Song("Avett Brothers", "Talk on Indolence", 2006),
            new Song("City and Colour", "Sleeping Sickness", 2007),
            new Song("Decemberists", "The Mariner's Revenge Song", 2005),
            new Song("Foo Fighters", "Baker Street", 1997),
            new Song("Gerry Rafferty", "Baker Street", 1978),
            new Song("Gerry Rafferty", "Baker Street", 1998),
            new Song("Queen", "Bohemian Rhapsody", 1975),
            new Song("Rogue Wave", "Love's Lost Guarantee", 2005));

      Comparator<Song> comp = Comparator.comparing(Song::getArtist).thenComparing(Song::getTitle)
            .thenComparing(Song::getYear);
      songList.sort(
            comp);

      assertEquals(songList, expectedList);
   }
}
