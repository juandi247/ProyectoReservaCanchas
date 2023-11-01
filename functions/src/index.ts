import * as functions from "firebase-functions";
import * as admin from "firebase-admin";

admin.initializeApp();

// Nombre de la colección de canchas
const COLLECTION_NAME = "Canchas";

// Función para eliminar fechas anteriores
export const eliminarFechasAnteriores = functions.firestore
  .document("${COLLECTION_NAME}/{canchaId}/Fechas/{fechaId}")
  .onCreate(async (snapshot, context) => {
    const fechaId = context.params.fechaId;
    const fechaActual = new Date().toISOString().split("T")[0];
    if (fechaId < fechaActual) {
      // La fecha es anterior a la fecha actual, así que la eliminamos
      const canchaId = context.params.canchaId;
      const fechaDocRef = admin
        .firestore()
        .collection(COLLECTION_NAME)
        .doc(canchaId)
        .collection("Fechas")
        .doc(fechaId); await fechaDocRef.delete();
    }
    return null;
  });
  