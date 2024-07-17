// Creamos la conexión a la base de datos
val jdbcHostname = "localhost"
val jdbcPort = 3306
val jdbcDatabase = "facturacion"
val jdbcUsername = "root"
val jdbcPassword = "password"
val jdbcUrl = s"jdbc:mysql://${jdbcHostname}:${jdbcPort}/${jdbcDatabase}?user=${jdbcUsername}&password=${jdbcPassword}"

val connection = DriverManager.getConnection(jdbcUrl)

try {
  // Creamos un objeto PreparedStatement para insertar datos en la tabla Factura
  val facturaStatement: PreparedStatement = connection.prepareStatement(
    "INSERT INTO Factura(num_factura, fecha, tipo_moneda, num_cliente) VALUES (?, ?, ?, ?)"
  )

  // Creamos un objeto PreparedStatement para insertar datos en la tabla Item
  val itemStatement: PreparedStatement = connection.prepareStatement(
    "INSERT INTO Item(id_producto, antiguedad, cantidad, valor_neto, num_factura) VALUES (?, ?, ?, ?, ?)"
  )

  // Creamos un objeto PreparedStatement para insertar datos en la tabla Trailer
  val trailerStatement: PreparedStatement = connection.prepareStatement(
    "INSERT INTO Trailer(num_items, valor_neto_total, num_factura) VALUES (?, ?, ?)"
  )

  // Leemos línea por línea del archivo
  for (line <- scala.io.Source.fromFile("facturas.txt").getLines()) {
    if (line.startsWith("H")) {
      // Extraemos los valores del header
      val Array(_, num_factura, num_cliente, fecha, tipo_moneda, _*) = line.split(" ")

      // Creamos un objeto Factura
      val factura = Factura(num_factura, fecha, tipo_moneda, num_cliente)

      // Añadimos la factura a la base de datos
      facturaStatement.setString(1, factura.num_factura)
      facturaStatement.setString(2, factura.fecha)
      facturaStatement.setString(3, factura.tipo_moneda)
      facturaStatement.setString(4, factura.num_cliente)
      facturaStatement.executeUpdate()

    } else if (line.startsWith("I")) {
      // Extraemos los valores del item
      val Array(_, id_producto, antiguedad, cantidad, valor_neto, num_factura, _*) = line.split(" ")

      // Creamos un objeto Item
      val item = Item(id_producto, antiguedad, cantidad, valor_neto, num_factura)

      // Añadimos el item a la base de datos
      itemStatement.setString(1, item.id_producto)
      itemStatement.setString(2, item.antiguedad)
      itemStatement.setString(3, item.cantidad)
      itemStatement.setString(4, item.valor_neto)
      itemStatement.setString(5, item.num_factura)
      itemStatement.executeUpdate()

    } else if (line.startsWith("T")) {
      // Extraemos los valores del trailer
      val Array(_, num_items, valor_neto_total, _, num_factura, _*) = line.split(" ")

      // Creamos un objeto Trailer
      val trailer = Trailer(num_items, valor_neto_total, num_factura)

      // Añadimos el trailer a la base de datos
      trailerStatement.setString(1, trailer.num_items)
      trailerStatement.setString(2, trailer.valor_neto_total)
      trailerStatement.setString(3, trailer.num_factura)
      trailerStatement.executeUpdate()

    }
  }
} catch {
  case e: SQLException => e.printStackTrace()
} finally {
  connection.close()
}
