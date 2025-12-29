package com.platzi_market.persistence.repository;

import com.platzi_market.domain.DTO.Purchase;
import com.platzi_market.domain.repository.PurchaseRepository;
import com.platzi_market.persistence.crud.CompraCrudRepository;
import com.platzi_market.persistence.entity.Compra;
import com.platzi_market.persistence.mapper.PurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CompraRepository implements PurchaseRepository {
    @Autowired
    private CompraCrudRepository compraCrudRepository;

    @Autowired
    private PurchaseMapper purchaseMapper;

    @Override
    public List<Purchase> getAll() {
        return this.purchaseMapper.toPurchases(this.compraCrudRepository.findAll());
    }

    @Override
    public Optional<List<Purchase>> getByClient(String clienteId) {
        return this.compraCrudRepository.findByIdCliente(clienteId).map(compras -> this.purchaseMapper.toPurchases(compras));
    }

    @Override
    public Purchase save(Purchase purchase) {
        Compra compra = this.purchaseMapper.toCompra(purchase);
        //Esto se hace primeramente por que nosotros no debemos y por ende NO recibimos el objeto "Compra" en cada item o productoCompra, ya que en el DTO así se estableció para mantener buenas prácticas, pero como al hacer la conversión si es necesario por que nuestra clase de entidad si lo exige entonces debemos establcerlo manualmente.
        // Usuario envía:
        // POST /api/purchases
        // {
        //     "clientId": "CLI123",
        //         "items": [
        //     { "productId": 100, "quantity": 5 },
        //     { "productId": 200, "quantity": 2 }
        //]
        // }
        //** Es decir esto se debe hacer cuando en el DTO omitimos atributos que no son lógicmamente correctos que el cliente nos envíe, pero es necesario establecer los demás atributos que son de la clase de entidad.
        // Los items NO necesitan decir "pertenezco a la compra X"
        // porque están dentro de la compra implícitamente
        compra.getComprasProductos().forEach(producto -> producto.setCompra(compra));

        // 1. Cascade
        //**Decimos, "quiero que cuando se guarde la compra de forma inmediata también se guarden los productosCompra que viene asociadas a ella"
        // Aquí pienso:
        // "Tengo una Compra con sus ComprasProducto ya vinculados.
        //  ¿Cómo los guardo?"
        // OPCIÓN 1 (SIN cascade):
        // "Tengo que guardar la compra, luego iterar y guardar cada item..."
        // → Mucho código, múltiples repositories, complejo

        // OPCIÓN 2 (CON cascade):
        // "¿Y si le digo a JPA que cuando guarde la Compra,
        //  automáticamente guarde también los items relacionados?"
        // → Un solo save(), simple, transaccional
        //Se usa cuando las entidades relacionadas (una o varias) son parte de la entidad padre y quieres que las operaciones se propaguen automáticamente
        //Si no hubiera usado cascade tendría que tener un repository que me permitiera guardar todos los ComprasPorductos para después asignarlos a la compra original y actualiza esta última
        // ** Se usa cuando una clase contiene UNA o más instancias de otra entidad y quiero que al guardar, actualizar o eliminar la principal también se haga ese cambio a esa(s) instancia(s) relacionada(s) de la otra entidad.
        // Ejemplo:
        //Claro y aquí:
        // @ManyToOne(fetch = FetchType.LAZY)
        // @JoinColumn(name = "id_cliente", updatable = false, insertable = false)
        // private Cliente cliente;
        // no tendría sentido poner cascade ya que estaríamos diciendo que cuando se cree un cliente entonces quiero que se gurden sus comprar, no tiene nada de sentido.


        //2. MapsId
        //Le dice a JPA:
        //"El campo idCompra de mi clave primaria compuesta (ComprasProductoPK.idCompra) debe tomar su valor automáticamente del ID de la entidad relacionada (compra.idCompra)"
        //@MapsId se usa cuando tienes una PK compuesta. Los productos están creados previamente (su PK ya existe), mientras que el idCompra se está procesando y no
        // existe aún. Con @MapsId le dices a JPA: 'toma el idCompra que se está generando en esta compra y úsalo en la PK compuesta de cada ComprasProducto
        //** Se debe usar cuando uno o más de los atributos de la clave primaria compuesta se esta generando (en proceso) como en este caso el de compra
        //✅ Solo cuando:
        //Tienes PK compuesta Y
        //Los campos de la PK son FK a otras entidades Y
        //Quieres sincronización automática (en lugar de manual)
        //@MapsId se usa cuando tienes una PK compuesta donde uno o más campos son FK a otras entidades. Le dice a JPA: "Toma automáticamente el ID de la entidad relacionada y úsalo para llenar ese campo en mi PK compuesta, en lugar de que yo lo establezca manualmente".


        //3. EAGER Y LAZY
        //lazy se limita a traer solo lo que le pido obviando traer datos sobre las relaciones que presenta en su entidad por otro lado eager trae lo que le pido e implícitamente mira con que esta realcionado en su clase de entidad y hace un join y trae todo
        //LAZY:
        //"Trae solo lo que le pido, ignorando las relaciones. Si después necesito los datos relacionados, hace otra consulta"
        //EAGER:
        //"Trae lo que le pido y automáticamente hace JOIN para traer también los datos relacionados, todo en una sola consulta"

        return this.purchaseMapper.toPurchase(this.compraCrudRepository.save(compra));
    }
}
