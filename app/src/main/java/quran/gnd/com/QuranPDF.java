package quran.gnd.com;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
public class QuranPDF extends AppCompatActivity{

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quran_pdf);

    }
    @Override
    protected void onPause() {
        super.onPause();


    }
    private int [] getPages(int pagesNum){
        int [] pages = new int[pagesNum];
        int j = 0;
        for (int i = pages.length-1; i >= 0 ; i--){
            pages[j] = i;
            j++;
        }
        return pages;
    }

    private void openPdf(String fileName,int page){
        /*
            try{
            //File file = getFileStreamPath(fileName);
            pdfView.fromAsset(fileName)
                    .defaultPage(page)
                    .pages(getPages(612))
                    .enableAnnotationRendering(true)
                    .scrollHandle(null)
                    .spacing(10) // in dp
                    .onPageChange(this)
                    .pageFitPolicy(FitPolicy.BOTH)
                    .swipeHorizontal(true)
                    .pageSnap(true)
                    .autoSpacing(true)
                    .pageFling(true)
                    .load();
        }catch(Exception e){
            e.printStackTrace();
        }

         */

    }
}