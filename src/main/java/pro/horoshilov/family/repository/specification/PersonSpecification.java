package pro.horoshilov.family.repository.specification;

abstract class PersonSpecification {
    //language=sql
    final static String SQL =
            "select \n" +
            "      p.person_id, \n" +
            "      p.birthday, \n" +
            "      p.death, \n" +
            "      p.avatar_id, \n" +
            "      p.description, \n" +
            "      p.name_first, \n" +
            "      p.name_last, \n" +
            "      p.name_middle, \n" +
            "      p.person_id, \n" +
            "      p.sex \n" +
            "from \n" +
            "      person p \n";
}
