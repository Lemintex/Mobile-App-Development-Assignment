//2715375
package android.cs.stir.ac.uk.yhproject

class Transaction {

    //ALL TRANSACTIONS WILL HAVE THIS INFORMATION
    var id:Int = 0
    var date:String?=null
    var fromTicker:String?=null
    var fromPrice:Float?=null
    var fromQuantity:Float?=null
    var toTicker:String?=null
    var toPrice:Float?=null
    var toQuantity:Float?=null

    //EMPTY CONSTRUCTOR TO CREATE A TRANSACTION BEFORE WRITING THE INFORMATION
    constructor(){

    }

    //TRANSACTION CONSTRUCTOR WITH ARGUMENTS
    constructor(date:String, fromTicker:String, fromPrice:Float, fromQuantity:Float, toTicker:String, toPrice:Float, toQuantity:Float) {
        this.date = date
        this.fromTicker = fromTicker
        this.fromPrice = fromPrice
        this.fromQuantity = fromQuantity
        this.toTicker = toTicker
        this.toPrice = toPrice
        this.toQuantity = toQuantity
    }
}