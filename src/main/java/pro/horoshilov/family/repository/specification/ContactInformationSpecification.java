package pro.horoshilov.family.repository.specification;

abstract class ContactInformationSpecification implements ISqlSpecification{
    //language=sql
    final static String SQL =
            "select \n" +
            "       ci.contact_information_id, \n" +
            "       ci.person_id, \n" +
            "       ci.code, \n" +
            "       ci.value, \n" +
            "       ci.type, \n" +
            "       ci.position \n" +
            "from \n" +
            "      contact_information ci\n ";
}
