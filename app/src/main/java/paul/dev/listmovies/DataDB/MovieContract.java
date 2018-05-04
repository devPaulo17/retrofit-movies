package paul.dev.listmovies.DataDB;

import android.provider.BaseColumns;

/**
 * Created by paulotrujillo on 5/1/18.
 */

public class MovieContract {



    public static abstract class MovieEntry implements BaseColumns {
        public static final String TABLE_NAME ="movies";

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String DESCRIPCION = "descripcion";
        public static final String IMAGE = "image";
        public static final String ORIGINAL_TITLE = "title";
        public static final String VOTE_COUNT = "count";
        public static final String VOTE_AVERAGE = "average";
        public static final String POPULARITY = "demand";
        public static final String LANGUAGES_ORIGINAL = "languajes";
        public static final String IMAGE_POSTER = "poster";
        public static final String DATE_PREMIER = "premier";
        public static final String HOMEPAGE = "homepage";
        public static final String CATEGORY = "category";

    }


}
