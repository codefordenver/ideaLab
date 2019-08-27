public interface CustomerinfoRepo extends CrudRepository<Customerinfo, Integer> {
    Customerinfo getCustomerinfoById(Integerid);
    Customerinfo save(Customerinfo colorType);
}