export interface Producto {
    // Definimos una interfaz para tipar los datos que vienen de la base de datos de Java
    id?: number; // opcional porque java lo genera al hacer POST
    nombre: string;
    precio: number;
}