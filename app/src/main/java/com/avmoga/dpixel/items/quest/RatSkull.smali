.class public Lcom/avmoga/dpixel/items/quest/RatSkull;
.super Lcom/avmoga/dpixel/items/Item;
.source "RatSkull.java"


# direct methods
.method public constructor <init>()V
    .registers 3

    .prologue
    .line 26
    invoke-direct {p0}, Lcom/avmoga/dpixel/items/Item;-><init>()V

    .line 29
    const-string v0, "name"

    const/4 v1, 0x0

    new-array v1, v1, [Ljava/lang/Object;

    invoke-static {p0, v0, v1}, Lcom/avmoga/dpixel/Messages/Messages;->get(Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/avmoga/dpixel/items/quest/RatSkull;->name:Ljava/lang/String;

    .line 30
    const/16 v0, 0xd0

    iput v0, p0, Lcom/avmoga/dpixel/items/quest/RatSkull;->image:I

    .line 32
    const/4 v0, 0x1

    iput-boolean v0, p0, Lcom/avmoga/dpixel/items/quest/RatSkull;->unique:Z

    .line 33
    return-void
.end method


# virtual methods
.method public info()Ljava/lang/String;
    .registers 3

    .prologue
    .line 47
    const-string v0, "desc"

    const/4 v1, 0x0

    new-array v1, v1, [Ljava/lang/Object;

    invoke-static {p0, v0, v1}, Lcom/avmoga/dpixel/Messages/Messages;->get(Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method public isIdentified()Z
    .registers 2

    .prologue
    .line 42
    const/4 v0, 0x1

    return v0
.end method

.method public isUpgradable()Z
    .registers 2

    .prologue
    .line 37
    const/4 v0, 0x0

    return v0
.end method

.method public price()I
    .registers 2

    .prologue
    .line 52
    const/16 v0, 0x12c

    return v0
.end method
