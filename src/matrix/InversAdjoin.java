// PROGRAM K14-GeprekMumbul-F07

// IDENTITAS
// Kelompok : 14 - Geprek Mumbul
// NIM/Nama - 1 : 13523021 - Muhammad Raihan Nazhim Oktana
// NIM/Nama - 2 : 13523005 - Muhammad Alfansya
// NIM/Nama - 3 : 13523065 - Dzaky Aurelia Fawwaz
// Instansi : Sekolah Teknik Elektro dan Informatika (STEI) Institut Teknologi Bandung (ITB)
// Jurusan : Teknik Informatika (IF)
// Nama File : InversAdjoin.java
// Topik : Tugas Besar 1 Aljabar Linier dan Geometri 2024 (IF2123-24)
// Tanggal : Rabu, 23 Oktober 2024
// Deskripsi : Subprogram F07 - Invers Adjoin
// Penanggung Jawab F07 : 13523021 - Muhammad Raihan Nazhim Oktana

// KAMUS
// matrix : package
// InversAdjoin : class
// Adjoin , Cofactor , inversAdjoin : function

// ALGORITMA
package matrix;
public class InversAdjoin {
    public static Matrix Cofactor (Matrix matrix) {
        // SPESIFIKASI LOKAL
        // Membuat matrix kofaktor dari matrix awal.

        // KAMUS LOKAL
        // getRow , getElmt , setElmt : procedure
        // DeterminantCOFACTORPass : function
        // matrix , ans : Matrix
        // i , j , n : integer

        // ALGORITMA LOKAL
        int i , j;
        int n = matrix.getRow();
		Matrix ans = new Matrix(n , n);
		for (i = 0 ; i < n ; i++) {
			for (j = 0 ; j < n ; j++) {
				ans.setElmt(i , j , Determinant.DeterminantCOFACTORPass(matrix , i , j));
				if (((i + j) % 2 == 1) && (ans.getElmt(i , j) != 0)) {
					ans.setElmt(i , j , ans.getElmt(i , j) * (-1));
                }
			}
		}
		return ans;
    }

    public static Matrix Adjoin (Matrix matrix) {
        // SPESIFIKASI LOKAL
        // Membuat matrix adjoin dari matrix awal.

        // KAMUS LOKAL
        // getRow , getCol , getElmt , setElmt : procedure
        // Cofactor : function
        // matrix , ans : Matrix
        // i , j : integer

        // ALGORITMA LOKAL
        int i , j;
        matrix = Cofactor(matrix);
        Matrix ans = new Matrix(matrix.getRow() , matrix.getCol());
        for (i = 0 ; i < matrix.getRow() ; i++) {
            for (j = 0 ; j < matrix.getCol() ; j++) {
                ans.setElmt(i , j , matrix.getElmt(j , i));
            }
        }
        return ans;
    }

    public static Matrix inversAdjoin (Matrix matrix) {
        // SPESIFIKASI LOKAL
        // Membuat matrix invers adjoin dari matrix awal.

        // KAMUS LOKAL
        // getRow , getCol , getElmt , setElmt : procedure
        // DeterminantCOFACTOR , Adjoin : function
        // matrix , ans : Matrix
        // i , j : integer
        // det : double

        // ALGORITMA LOKAL
        int i , j;
		Matrix ans = Adjoin(matrix);
		double det = Determinant.DeterminantCOFACTOR(matrix);
		for (i = 0 ; i < matrix.getRow() ; i++) {
			for (j = 0 ; j < matrix.getCol() ; j++) {
				ans.setElmt(i , j , (ans.getElmt(i , j) / det));
			}
		}
		return ans;
    }
}
