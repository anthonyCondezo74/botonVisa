<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Integraciones VisaNet</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
<body>
	<div class="container">
		<h1>Ejemplo botón de pago</h1>
		<hr>

		<div class="row">
			<div class="col-md-12">
				<form class="card" action="IndexServlet" method="post">
					<div class="card-body">
						<div class="row">
							<div class="col-md-3">
								<div class="form-group">
									<label>Código comercio:</label> <input type="text"
										class="form-control" value="522591303" name="merchantId">
								</div>
							</div>
							<div class="col-md-3">
								<div class="form-group">
									<label>Moneda:</label> <select class="form-control"
										name="currency">
										<option value="PEN" selected>Soles</option>
										<option value="USD">Dolares</option>
									</select>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="col-md-3">
								<div class="form-group">
									<label>Nombre:</label> <input type="text" class="form-control"
										value="Integraciones" name="nombre">
								</div>
							</div>
							<div class="col-md-3">
								<div class="form-group">
									<label>Apellido:</label> <input type="text"
										class="form-control" value="VisaNet" name="apellido">
								</div>
							</div>
							<div class="col-md-3">
								<div class="form-group">
									<label>Importe:</label> <input type="text" class="form-control"
										value="1.00" name="importe">
								</div>
							</div>
							<div class="col-md-3">
								<div class="form-group">
									<label>Email:</label> <input type="text"
										class="form-control" value="migraciones.visanet@necomplus.com" name="email">
								</div>
							</div>
						</div>

						<div class="row">
							<div class="col-md-3">
								<div class="form-group">
									<input type="submit" class="btn btn-outline-success btn-block"
										value="Crear botón">
								</div>
							</div>
						</div>

					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>